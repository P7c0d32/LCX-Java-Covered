public class Main {

    public static void main (String[] args) {
        String usages = "Usage: java -X.jar -listen <InPort> <OutPort>\n" +
                        "                   -slave <Remote Host> <Remote Port> <TargetHost> <Target Port>\n" +
                        "                   -tran <LocalPort> <TargetHost> <TargetPort>";

        if (args.length == 0) {
            System.out.println(usages);
            System.exit(0);
        }

        //开始解析命令行参数
        switch (args[0]) {
            case "-listen"://listen模式
                if(args.length == 3) {
                    LCX lcx = new LCX();
                    lcx.setLocalInPort(Integer.valueOf(args[1]));
                    lcx.setLocalPort(Integer.valueOf(args[2]));
                    lcx.listen();
                } else {
                    System.out.println(usages);
                }
                break;
            case "-slave"://slave模式
                if (args.length == 5) {
                    LCX lcx = new LCX();
                    lcx.setRemoteHost(args[1]);
                    lcx.setRemotePort(Integer.valueOf(args[2]));
                    lcx.setTargetHost(args[3]);
                    lcx.setTargetPort(Integer.valueOf(args[4]));
                    lcx.slave();
                } else {
                    System.out.println(usages);
                }
                break;
            case "-tran"://tran模式
                if (args.length == 4) {
                    LCX lcx = new LCX();
                    lcx.setLocalPort(Integer.valueOf(args[1]));
                    lcx.setTargetHost(args[2]);
                    lcx.setTargetPort(Integer.valueOf(args[3]));
                    lcx.tran();
                } else {
                    System.out.println(usages);
                }
                break;
            default:
                System.out.println(usages);
        }
    }
}
