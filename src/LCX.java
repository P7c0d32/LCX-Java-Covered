import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

public class LCX {
    public String RemoteHost = "";
    public int RemotePort = 0;
    public String TargetHost = "";
    public int TargetPort = 0;
    public int LocalPort = 0;
    public int LocalInPort = 0;

    public void setRemoteHost(String remoteHost) {
        this.RemoteHost = remoteHost;
    }

    public void setRemotePort(int remotePort) {
        this.RemotePort = remotePort;
    }

    public void setTargetHost(String targetHost) {
        this.TargetHost = targetHost;
    }

    public void setTargetPort(int targetPort) {
        this.TargetPort = targetPort;
    }

    public void setLocalPort(int localPort) {
        this.LocalPort = localPort;
    }

    public void setLocalInPort(int localInPort) {
        this.LocalInPort = localInPort;
    }

    public void listen() {
        System.out.println("listen服务已启动");
            try {
                ServerSocket inss = new ServerSocket(LocalInPort);
                ServerSocket outss = new ServerSocket(LocalPort);
                Socket ins = inss.accept();
                Socket outs = outss.accept();
                IntoOut in = new IntoOut(ins,outs);
                IntoOut out = new IntoOut(outs,ins);
                in.start();
                out.start();
                while (in.isAlive() && out.isAlive()) {
                    Thread.sleep(5000);
                }
                inss.close();
                outss.close();
                ins.close();
                outs.close();
                System.out.println("重启listen服务...");
                listen();
            } catch (ConnectException ce) {
                System.out.println("连接被拒绝!");
                System.out.println("重启listen服务...");
                listen();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Listen函数异常!");
                System.out.println("重启listen服务...");
                listen();
            }
    }

    public void slave() {
        try {
            Socket ts = new Socket(TargetHost,TargetPort);
            Socket rs = new Socket(RemoteHost,RemotePort);
            IntoOut t = new IntoOut(ts,rs);
            IntoOut r = new IntoOut(rs,ts);
            t.start();
            r.start();
            while (t.isAlive() && r.isAlive()) {
                Thread.sleep(5000);
                System.out.println("Alive!");
            }
            ts.close();
            rs.close();
            System.out.println("重启slave服务...");
            slave();
        } catch (ConnectException ce) {
            System.out.println("连接被拒绝!");
            System.out.println("重启slave服务...");
            slave();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("重启slave服务...");
            slave();
        }
    }

    public void tran() {
        System.out.println("tran服务已启动");
            try {
                Socket ins = new Socket(TargetHost,TargetPort);
                ServerSocket ss = new ServerSocket(LocalPort);
                Socket outs = ss.accept();
                IntoOut in = new IntoOut(ins,outs);
                IntoOut out = new IntoOut(outs,ins);
                in.start();
                out.start();
                while ((in.isAlive() && out.isAlive())) {
                    Thread.sleep(5000);
                }
                ins.close();
                ss.close();
                outs.close();
                System.out.println("重启tran服务...");
                tran();
            } catch (ConnectException ce) {
                System.out.println("连接被拒绝!");
                System.out.println("重启tran服务...");
                tran();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("tran函数异常!");
                System.out.println("重启tran服务...");
                tran();
            }
    }


}
