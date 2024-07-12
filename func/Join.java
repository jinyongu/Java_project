package func;

import java.io.*;

public class Join {
    private String str;
    private boolean again = false;

    public int isJoin(String info) {
        String[] partsIn = info.split(" ");
        String idIn = partsIn[0];
        String passIn = partsIn[1];
        String id = "";
        String pass = "";

        FileInputStream in;
        try {
            in = new FileInputStream("information.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while((str = br.readLine()) != null) {
                String[] parts = str.split(" ");
                id = parts[0];
                pass = parts[1];

                if (id.equals(idIn)) {
                    again = true;
                    break;
                }
            };
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (again) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isPassword(String password) {
        int cn = 0;
        int Cn = 0;
        int nn = 0;
        int sn = 0;

        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) >= 'A' && password.charAt(i) <= 'z') {
                cn++;
            } else if (password.charAt(i) >= '0' && password.charAt(i) <= '9') {
                nn++;
            } else {
                sn++;
            }
        }

        if (sn == 0) {
            return false;
        }

        return true;
    }

}
