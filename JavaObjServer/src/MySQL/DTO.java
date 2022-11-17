package MySQL;

// 사용자의 정보 setting (getter, setter)
public class DTO {
    private String name, remain, room;
    
    public String getName() { return name; }

    public String getRemain() { return remain; }

    public String getRoom() { return room; }

    public void setName(String name) { this.name = name; }

    public void setRemain(String remain) { this.remain = remain; }

    public void setRoom(String room) { this.room = room; }
}
