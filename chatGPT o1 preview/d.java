private void loadDataFromFile() {
    try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            String role = data[4]; // Assuming role is stored in the 5th column
            if (role.equals("Student")) {
                userList.add(new Student(data[0], data[1], data[2], data[3], data[5]));
            } else if (role.equals("Lecturer")) {
                userList.add(new Lecturer(data[0], data[1], data[2], data[3], data[5]));
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
