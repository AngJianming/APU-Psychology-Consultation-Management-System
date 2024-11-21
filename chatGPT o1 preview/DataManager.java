// DataManager.java
public class DataManager {
    public static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
            String userData = user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getEmail();
            writer.write(userData);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User loadUser(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equals(username)) {
                    // Create User object based on data
                    return new Student(data[0], data[1], data[2], data[3], "studentID");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
