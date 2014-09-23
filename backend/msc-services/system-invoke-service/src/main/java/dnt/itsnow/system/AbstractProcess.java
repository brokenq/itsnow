/**
 * Developer: Kadvin Date: 14-9-21 下午4:40
 */
package dnt.itsnow.system;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.SystemInvocation;
import dnt.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * <h1>The abstract shell inherited by local/remote shell</h1>
 */
public abstract class AbstractProcess<T extends SystemInvocation> implements Process {
    protected final ProcessBuilder builder = new ProcessBuilder();
    protected final ExecutorService systemInvokeExecutor;
    protected final T               invocation;
    protected java.lang.Process     underlying;

    public AbstractProcess(T invocation, ExecutorService systemInvokeExecutor) {
        this.invocation = invocation;
        this.systemInvokeExecutor = systemInvokeExecutor;
    }

    protected List<String> getCommands(String command, Object[] args) {
        List<String> commands = new LinkedList<String>();
        commands.add(command);
        for (Object arg : args) {
            commands.add(arg.toString());
        }
        return commands;
    }

    @Override
    public int run(String command, Object... args) throws SystemInvokeException {
        String[] realCommands = assembleCommand(command, args);
        builder.command(realCommands);
        try {
            underlying = builder.start();
            Future<?> stderrFuture = pipe(underlying.getErrorStream(), errorFile(), totalFile());
            Future<?> stdoutFuture = pipe(underlying.getInputStream(), outFile(), totalFile());
            int exitCode = underlying.waitFor();
            stderrFuture.get();
            stdoutFuture.get();
            return exitCode;
        } catch (IOException e) {
            recordError(e, errorFile());
            throw new SystemInvokeException("Can't run the command: " + command, e);
        } catch (InterruptedException e) {
            recordError(e, errorFile());
            throw new SystemInvokeException("Command execution is interrupted", e);
        } catch (ExecutionException e) {
            recordError(e, errorFile());
            throw new SystemInvokeException("Command execution exception", e);
        }
    }

    private void recordError(Exception e, File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            recordError(e, fos, 0);
        } catch (Exception e1) {
            //ignore
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }

    private void recordError(Throwable e, OutputStream outputStream, int indent) throws IOException {
        for (int i = 0; i < indent; i++)
            IOUtils.write("\t", outputStream);
        IOUtils.write(e.getClass().getName().getBytes(), outputStream);
        IOUtils.write(":", outputStream);
        IOUtils.write(e.getLocalizedMessage().getBytes(Charset.forName("UTF8")), outputStream);
        IOUtils.write("\n", outputStream);
        if (e.getCause() != null && e.getCause() != e) {
            recordError(e.getCause(), outputStream, indent + 1);
        }
    }

    protected File totalFile() {
        return new File(System.getProperty("APP_HOME"), "tmp/" + this.invocation.getTotalFileName());
    }

    protected File outFile() {
        return new File(System.getProperty("APP_HOME"), "tmp/" + this.invocation.getOutFileName());
    }

    protected File errorFile() {
        return new File(System.getProperty("APP_HOME"), "tmp/" + this.invocation.getErrFileName());
    }

    @Override
    public String getOutput() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(outFile());
            return StringUtils.join(IOUtils.readLines(fis), "\n");
        } catch (Exception e) {
            return null;
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    @Override
    public String getError() {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(errorFile());
            return StringUtils.join(IOUtils.readLines(fis), "\n");
        } catch (Exception e) {
            return null;
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    protected abstract String[] assembleCommand(String command, Object[] args);

    public List<String> getCommand() {
        return builder.command();
    }

    private Future<?> pipe(final InputStream src, final File file, File totalFile) {
        return systemInvokeExecutor.submit(new Redirector(src, file, totalFile));
    }

    private static class Redirector implements Runnable {
        private final InputStream src;
        private final File        file1;
        private final File        file2;

        public Redirector(InputStream src, File file1, File file2) {
            this.src = src;
            this.file1 = file1;
            this.file2 = file2;
        }

        public void run() {
            FileOutputStream dest1 = null;
            FileOutputStream dest2 = null;
            try {
                if (!file1.exists()) FileUtils.touch(file1);
                if (!file2.exists()) FileUtils.touch(file2);
                dest1 = new FileOutputStream(file1, true);
                dest2 = new FileOutputStream(file2, true);
                byte[] buffer = new byte[1024];
                for (int n = 0; n != -1; n = src.read(buffer)) {
                    dest1.write(buffer, 0, n);
                    dest2.write(buffer, 0, n);
                }
            } catch (IOException e) {
                // just exit
            } finally {
                IOUtils.closeQuietly(dest1);
                IOUtils.closeQuietly(dest2);
            }
        }
    }
}
