
public class Task {
    public static void main(String[] args) {
        Building building = new Buildings("Офис");
        building.addRoom("Переговорная", 20, 3);
        building.getRoom("Переговорная").add(new Lamp(200));
        building.getRoom("Переговорная").add(new Lamp(2500));
        building.getRoom("Переговорная").add(new Table("Стол письменный", 3));
        building.getRoom("Переговорная").add(new Table("Журнальный стол", 1, 6));

        building.addRoom("Кухня", 550, 5);
        building.getRoom("Кухня").add(new Cupboard("Шкаф для посуды", 5));

        building.addRoom("Уборная", 6, 0);
        building.getRoom("Уборная").add(new Lamp(250));

        building.addRoom("Гостинная", 20, 5);
        building.getRoom("Гостинная").add(new Lamp(120));
        building.describe();
    }
}
