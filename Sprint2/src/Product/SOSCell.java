package Product;

import java.util.Objects;

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
    return Objects.equals(this.currentContent, "");
  }

  public void setContent(String content){
    this.currentContent = content;
  }
  
  public String getContent(){
    return this.currentContent;
  }

  public void setCellOwner(int playerID){
    this.cellOwnerID = playerID;
  }

  public int getCellOwner(){
    return this.cellOwnerID;
  }

  public void setBeginIndexOfSOS(int row, int col){
    this.beginRowIndex = row;
    this.beginColIndex = col;
  }

  public void setEndIndexOfSOS(int row, int col){
    this.endRowIndex = row;
    this.endColIndex = col;
  }

  public int getBeginRowIndex(){
    return this.beginRowIndex;
  }

  public int getEndRowIndex(){
    return this.endRowIndex;
  }

  public int getBeginColIndex(){
    return this.beginColIndex;
  }

  public int getEndColIndex(){
    return this.endColIndex;
  }
}