/**
 * Developer: Kadvin Date: 14-9-21 下午4:40
 */
package dnt.itsnow.system;

import dnt.itsnow.exception.SystemInvokeException;
import dnt.itsnow.model.SystemInvocation;
import dnt.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

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
        String realCommand = assembleCommand(command, args);
        invocation.recordCommand(realCommand);
        builder.command(realCommand);
        try {
            final java.lang.Process process = builder.start();
            final File stdoutFile = new File(System.getProperty("APP_HOME") + "/tmp", this.invocation.getId() + ".out");
            final File stderrFile = new File(System.getProperty("APP_HOME") + "/tmp", this.invocation.getId() + ".err");
            pipeOutput(process, stdoutFile,  stderrFile);

            return process.waitFor();
        } catch (IOException e) {
            throw new SystemInvokeException("Can't run the command: " + command, e);
        } catch (InterruptedException e) {
            throw new SystemInvokeException("Command execution is interrupted", e);
        }
    }

    protected abstract String assembleCommand(String command, Object[] args);

    private void pipeOutput(java.lang.Process process, File stdout, File stderr) {
        pipe(process.getErrorStream(), stderr);
        pipe(process.getInputStream(), stdout);
    }

    private void pipe(final InputStream src, final File file) {
        systemInvokeExecutor.submit(new Runnable() {
            public void run() {
                FileOutputStream dest = null;
                try {
                    dest = new FileOutputStream(file, true);
                    String beginLine = invocation.toString();
                    List<String> lines = new ArrayList<String>(1);
                    lines.add(beginLine);
                    IOUtils.writeLines(lines, "\n", dest, Charset.forName("UTF8"));
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
