package Product;

public class SOSCell{
  String currentContent;
  public SOSCell(){
    this.setContent("");
  }

  public boolean isEmpty(){
    return currentContent == "";
  }

  public void setContent(String content){
    currentContent = content;
  }
  
  public String getContent(){
    return currentContent;
  }
}