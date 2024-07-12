package func;

import java.io.*;

public class Login {
    private String str;
    private boolean hasId = false;

    public int isLogin(String info) {
        String[] partsIn = info.split(" ");
        String idIn = partsIn[0];
        String passIn = partsIn[1];
        String id = "";
        String pass = "";

        Person a = new Person(idIn, passIn);

        try {
            ObjectOutputStream outputStream =
                    new ObjectOutputStream(new FileOutputStream("information.ser"));
            outputStream.writeObject(a);
            outputStream.close();
        } catch (IOException e) {
            System.err.println("Error writing to file.");
            System.exit(0);
        }

        FileInputStream in;
        try {
            in = new FileInputStream("information.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            while((str = br.readLine()) != null) {
                String[] parts = str.split(" ");
                id = parts[0];
                pass = parts[1];

                if (id.equals(idIn)) {
                    hasId = true;
                    break;
                }
            };
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Person person = null;

        try {
            ObjectInputStream inputStream =
                    new ObjectInputStream(new FileInputStream("information.ser"));
            person = (Person)inputStream.readObject();
            inputStream.close();
        } catch (FileNotFoundException e ) {
            System.err.println("File not found.");
            System.exit(0);
        } catch (ClassNotFoundException e) {
            System.err.println("Class not found.");
            System.exit(0);
        }catch (IOException e) {
            System.err.println("Error reading file.");
            System.exit(0);
        }


        if (hasId) {
            if (person.getPassword().equals(pass)) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 2;
        }
    }
}
