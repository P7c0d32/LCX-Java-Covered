import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class IntoOut extends Thread {
    public Socket in = null;
    public Socket out = null;
    public InputStream ins = null;
    public OutputStream outs = null;
    public String inAddress = "";
    public String outAddress = "";

    public IntoOut(Socket in,Socket out) {
        this.in = in;
        this.out = out;
        this.inAddress = this.in.getRemoteSocketAddress().toString().replace("/","");
        this.outAddress = this.out.getRemoteSocketAddress().toString().replace("/","");
    }

    @Override
    public void run() {
        System.out.println("线程启动:" + inAddress + "--->" + outAddress  );
            try {
                ins = in.getInputStream();
                outs = out.getOutputStream();
                byte[] data = new byte[2048];

                int counter = 0;
                while (true) {
                    int len = ins.read(data);
                    if (len < 0 ) {
                        Thread.sleep(3000);
                        counter += 1;
                        if (counter >= 4) {
                            break;
                        }
                        continue;
                    } else {
                        counter = 0;
                    }
                    outs.write(data, 0, len);
                    outs.flush();
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("线程关闭:" + inAddress + "--->" + outAddress  );
            } finally {
                System.out.println("线程关闭:" + inAddress + "--->" + outAddress  );
            }
    }
}
