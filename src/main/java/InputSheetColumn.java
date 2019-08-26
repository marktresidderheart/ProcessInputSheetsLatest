public class InputSheetColumn {

    private String columnName;
    private int columnNumber;

    public InputSheetColumn(String columnName, int columnNumber) {
        this.columnName = columnName;
        this.columnNumber = columnNumber;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getColumnNumber() {
        return columnNumber;
    }

    public void setColumnNumber(int columnNumber) {
        this.columnNumber = columnNumber;
    }
}
