package Product;

public class SOSCell{
  private String currentContent;
  private int cellOwnerID = -1;
  private int beginRowIndex = -1;
  private int endRowIndex = -1;
  private int beginColIndex = -1;
  private int endColIndex = -1;

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

  public void setCellOwner(int playerID){
    cellOwnerID = playerID;
  }

  public int getCellOwner(){
    return cellOwnerID;
  }

  public void setBeginIndexOfSOS(int row, int col){
    beginRowIndex = row;
    beginColIndex = col;
  }

  public void setEndIndexOfSOS(int row, int col){
    endRowIndex = row;
    endColIndex = col;
  }

  public int getBeginRowIndex(){
    return beginRowIndex;
  }

  public int getEndRowIndex(){
    return endRowIndex;
  }

  public int getBeginColIndex(){
    return beginColIndex;
  }

  public int getEndColIndex(){
    return endColIndex;
  }
}