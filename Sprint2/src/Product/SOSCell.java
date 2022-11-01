package Product;

public class SOSCell{
  private String currentContent;
  private boolean isSOS = false;
  private int cellOwnerID = -1;
  private int beginIndexOfSOS = -1;
  private int endIndexOfSOS = -1;
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

  public void setIsSOS(){
    if(isSOS){
      isSOS = false;
    }
    else{
      isSOS = true;
    }
  }

  public boolean isSOS(){
    return isSOS;
  }

  public void setCellOwner(int playerID){
    cellOwnerID = playerID;
  }

  public int getCellOwner(){
    return cellOwnerID;
  }

  public void setBeginIndexOfSOS(int beginIndex){
    beginIndexOfSOS = beginIndex;
  }

  public void setEndIndexOfSOS(int endIndex){
    endIndexOfSOS = endIndex;
  }

  public int getBeginIndexOfSOS(){
    return beginIndexOfSOS;
  }

  public int getEndIndexOfSOS(){
    return endIndexOfSOS;
  }
}