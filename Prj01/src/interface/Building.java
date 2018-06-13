
interface Building {
    void addRoom(String roomName, Double space, Integer window);
    void addRoom(String roomName, Integer space, Integer window);
    Room getRoom(String name);
    void describe();
}
