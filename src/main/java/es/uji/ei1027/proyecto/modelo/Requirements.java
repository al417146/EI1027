package es.uji.ei1027.proyecto.modelo;

public class Requirements{
    int idRequirement;
    String topic;
    String desc;


    public int getIdRequirement() {
        return idRequirement;
    }

    public void setIdRequirement(int idRequirement) {
        this.idRequirement = idRequirement;
    }

    public String getTopic() { return topic; }

    public void setTopic(String topic) {  this.topic = topic; }

    public String getDesc() {  return desc; }

    public void setDesc(String desc) {   this.desc = desc; }

    @Override
    public String toString() {
        return "Requirements {" +
                " idRequirement ='" + idRequirement + '\'' +
                ", topic ='" + topic + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
