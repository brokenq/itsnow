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
    protected final ExecutorService  systemInvokeExecutor;
    protected final T invocation;

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
        invocation.recordCommand(StringUtils.join(realCommands," "));
        builder.command(realCommands);
        try {
            final java.lang.Process process = builder.start();
            Future<?> stderrFuture = pipe(process.getErrorStream(), errorFile());
            Future<?> stdoutFuture = pipe(process.getInputStream(), outFile());
            int exitCode = process.waitFor();
            stderrFuture.get();
            stdoutFuture.get();
            return exitCode;
        } catch (IOException e) {
            throw new SystemInvokeException("Can't run the command: " + command, e);
        } catch (InterruptedException e) {
            throw new SystemInvokeException("Command execution is interrupted", e);
        } catch (ExecutionException e) {
            throw new SystemInvokeException("Command execution exception", e);
        }
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

    private Future<?> pipe(final InputStream src, final File file) {
        return systemInvokeExecutor.submit(new Runnable() {
            public void run() {
                FileOutputStream dest = null;
                try {
                    if( !file.exists() ) FileUtils.touch(file);
                    dest = new FileOutputStream(file, true);
                    byte[] buffer = new byte[1024];
                    for (int n = 0; n != -1; n = src.read(buffer)) {
                        dest.write(buffer, 0, n);
                    }
                } catch (IOException e) { // just exit
                } finally {
                    IOUtils.closeQuietly(dest);
                }
            }
        });
    }

}
