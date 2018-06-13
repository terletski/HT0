import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Buildings implements Building {
    private String name;
    private List<Room> roomList = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    public Buildings(String name) {
        this.name = name;
    }

    @Override
    public void addRoom(String roomName, Double space, Integer window) {
        roomList.add(new Room(roomName, space, window));
    }

    @Override
    public void addRoom(String roomName, Integer space, Integer window) {
        roomList.add(new Room(roomName, space, window));
    }

    @Override
    public Room getRoom(String name) {
        for (Room room : roomList) {
            if (name.equals(room.getName())) {
                return room;
            }
        }
        return new Room();
    }

    @Override
    public void describe() {
        System.out.println(name);
        for (Room room : roomList) {
            if(room.getErrorList().size()>0){
                errorList.addAll(room.getErrorList());
            }
            if (room.isRoomValid()) {
                if (room.getIllumination() >= Room.MIN_ILLUMINATION) {
                    printIllumination(room);
                    printSpace(room);
                    printFurniture(room);
                } else {
                    try {
                        throw new IlluminanceTooMuchException("Освещенность в комнате " + room.getName() +
                                " ниже предела (" + Room.MIN_ILLUMINATION +
                                " лк) и равна " + room.getIllumination() + " лк." + "\n" +
                                "Добавьте количество окон в помещении " + name +
                                " или установите дополнительные лампочки общей освещенностью не менее " +
                                (Room.MIN_ILLUMINATION - room.getIllumination()) + " лк."
                        );
                    } catch (IlluminanceTooMuchException e) {
                        errorList.add(e.getMessage());
                    }
                }
            }
        }
        if(errorList.size() > 0){
            System.out.println();
            System.out.println("Несоответствия требованиям:");
            for (String message : errorList) {
                System.out.println(message);
            }
        }
    }

    private void printFurniture(Room room) {
        if (room.isRoomValid()) {
            int furnitureQuantity = room.getFurnitureList().size();
            if (furnitureQuantity > 0) {
                System.out.println("  Мебель:");
                for (Furniture furniture : room.getFurnitureList()) {
                    System.out.print("   " + furniture.getName() + " (площадь ");
                    if (Objects.equals(furniture.getMinSpace(), furniture.getMaxSpace())) {
                        System.out.print(furniture.getMaxSpace());
                    } else {
                        System.out.print("от " + furniture.getMinSpace() + " м^2 до " + furniture.getMaxSpace());
                    }
                    System.out.println(" м^2)");
                }
            } else {
                System.out.println("  Мебели нет.");
            }
        }
    }

    private void printSpace(Room room) {
        if (room.isRoomValid()) {
            System.out.print("  Площадь = " + room.getSpace() + " м^2 (");
            if (room.getFurnitureList().isEmpty()) {
                System.out.println("свободно 100%)");
            } else {
                if (Objects.equals(room.getMinFreeSpace(), room.getMaxFreeSpace())) {
                    System.out.println("занято " + room.calculateMinFurnitureSpace() +
                            " м^2, свободно " + room.getMinFreeSpace() +
                            " м^2 или " + (int) (100 * room.getMinFreeSpace() / room.getSpace()) + "% площади)");
                } else {
                    System.out.println("занято " + room.calculateMinFurnitureSpace() + "-" +
                            room.calculateMaxFurnitureSpace() + " м^2, свободно " + room.getMinFreeSpace() +
                            " м^2 или " + (int) (100 * room.getMinFreeSpace() / room.getSpace()) + "% площади)");
                }
            }
        }
    }

    private void printIllumination(Room room) {
        if (room.isRoomValid()) {
            System.out.print(" " + room.getName() + "\n" +
                    "  Освещенность = " + room.getIllumination() + " (");
            if (room.getWindow() == 0) {
                System.out.print("окон нет");
            } else {
                System.out.print(room.getWindow() + " окна по " + Room.WINDOW_ILLUMINATION + " лк");
            }
            int lampQuantity = room.getLampList().size();
            if (lampQuantity > 0) {
                if (lampQuantity == 1) {
                    System.out.print(", лампочка ");
                } else {
                    System.out.print(", лампочки ");
                }

                for (int i = 0; i < lampQuantity; i++) {
                    System.out.print(room.getLampList().get(i).getIllumination() + " лк");
                    if (i < lampQuantity - 1) {
                        System.out.print(" и ");
                    }
                }
            } else {
                System.out.print(", лампочек нет");
            }
            System.out.println(")");
        }
    }
}
