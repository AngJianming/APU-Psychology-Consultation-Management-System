private void saveDataToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
        for (User user : userList) {
            String role = user instanceof Student ? "Student" : "Lecturer";
            String userID = user instanceof Student ? ((Student) user).getStudentID() : ((Lecturer) user).getLecturerID();
            String data = user.getUsername() + "," + user.getPassword() + "," + user.getName() + "," + user.getEmail() + "," + role + "," + userID;
            writer.write(data);
            writer.newLine();
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
