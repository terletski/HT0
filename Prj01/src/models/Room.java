import java.util.ArrayList;
import java.util.List;

public class Room {
    public static final Integer WINDOW_ILLUMINATION = 700;
    public static final Integer MAX_ILLUMINATION = 4000;
    public static final Integer MIN_ILLUMINATION = 300;
    public static final Integer MAX_ROOM_FILLING_PERCENT = 70;

    private boolean isRoomValid = false;
    private String name;
    private Double space, minFreeSpace = 0.0, maxFreeSpace = 0.0;
    private Integer window;
    private Integer illumination = 0;
    private List<Lamp> lampList = new ArrayList<>();
    private List<Furniture> furnitureList = new ArrayList<>();
    private List<String> errorList = new ArrayList<>();

    public Double calculateMaxFurnitureSpace() {
        Double sumFurnitureSpace = 0.0;
        if (furnitureList.size() > 0) {
            for (Furniture furniture : furnitureList) {
                sumFurnitureSpace += furniture.getMaxSpace();
            }
        }
        return sumFurnitureSpace;
    }

    public Double calculateMinFurnitureSpace() {
        Double sumFurnitureSpace = 0.0;
        if (furnitureList.size() > 0) {
            for (Furniture furniture : furnitureList) {
                sumFurnitureSpace += furniture.getMinSpace();
            }
        }
        return sumFurnitureSpace;
    }

    public List<Lamp> getLampList() {
        return lampList;
    }

    public Integer getWindow() {
        return window;
    }

    public Integer getIllumination() {
        return illumination;
    }

    public String getName() {
        return name;
    }

    public Room() {
    }

    public boolean isRoomValid() {
        return isRoomValid;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public Room(String name, Double space, Integer window) {
        if (window * WINDOW_ILLUMINATION > MAX_ILLUMINATION) {
            try {
                throw new IlluminanceTooMuchException("Превышено допустимое значение освещенности в комнате " + name + "\n" +
                        "Значение освещенности не должно превышать " + MAX_ILLUMINATION + " лк.\n" +
                        "Укажите количество окон в помещении " + name + " менее " + MAX_ILLUMINATION / WINDOW_ILLUMINATION + ".");
            } catch (IlluminanceTooMuchException e) {
                errorList.add(e.getMessage());
            }
        } else {
            this.name = name;
            this.space = space;
            this.window = window;
            this.illumination = window * WINDOW_ILLUMINATION;
            this.isRoomValid = true;
        }
    }

    public Room(String name, Integer space, Integer window) {
        if (window * WINDOW_ILLUMINATION > MAX_ILLUMINATION) {
            try {
                throw new IlluminanceTooMuchException("Превышено допустимое значение освещенности в комнате " + name + "\n" +
                        "Значение освещенности не должно превышать " + MAX_ILLUMINATION + " лк.\n" +
                        "Укажите количество окон в помещении " + name + " менее " + MAX_ILLUMINATION / WINDOW_ILLUMINATION + ".");
            } catch (IlluminanceTooMuchException e) {
                errorList.add(e.getMessage());
            }
        } else {
            this.illumination = window * WINDOW_ILLUMINATION;
            this.name = name;
            this.space = (double) space;
            this.window = window;
            this.isRoomValid = true;
        }
    }

    public void add(Lamp lamp) {
        lampList.add(lamp);
        illumination += lamp.getIllumination();
    }

    public void add(Furniture furniture) {
        if (isRoomValid) {
            furnitureList.add(furniture);
            if (minFreeSpace != 0.0) {
                minFreeSpace -= furniture.getMaxSpace();
            } else {
                minFreeSpace = space - furniture.getMaxSpace();
            }
            if (maxFreeSpace != 0.0) {
                maxFreeSpace -= furniture.getMinSpace();
            } else {
                maxFreeSpace = space - furniture.getMinSpace();
            }
            if ((100 * (space - minFreeSpace) / space) > MAX_ROOM_FILLING_PERCENT) {   //условие превышения площади мебели
                isRoomValid = false;
                try {
                    throw new SpaceUsageTooMuchException("Общая площадь мебели в комнате " + name +
                            " превышает допустимую (" + MAX_ROOM_FILLING_PERCENT + "% от всей площади комнаты) " +
                            "и равен " + Math.round(100 * (space - minFreeSpace) / space) + "%");
                } catch (SpaceUsageTooMuchException e) {
                    errorList.add(e.getMessage());
                }
            }
        }
    }

    public Double getSpace() {
        return space;
    }

    public Double getMinFreeSpace() {
        if (furnitureList.size() > 0) {
            return minFreeSpace;
        }
        return space;
    }

    public List<Furniture> getFurnitureList() {
        return furnitureList;
    }

    public Double getMaxFreeSpace() {
        return maxFreeSpace;
    }
}
