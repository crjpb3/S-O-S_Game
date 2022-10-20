package Product;

public class SOSCell{
  public enum CellContent {NULL, S_CHAR, O_CHAR}
  CellContent currentContent;
  public SOSCell(){
    this.setContent(CellContent.NULL);
  }

  public boolean isEmpty(){
    return currentContent == CellContent.NULL;
  }

  public void setContent(CellContent content){
    currentContent = content;
  }
  
  public CellContent getContent(){
    return currentContent;
  }
}
