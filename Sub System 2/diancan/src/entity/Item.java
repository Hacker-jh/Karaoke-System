package entity;


public class Item {
    private String ID;
    private int Amount;
    private String Name;
    private String Price;
    private int order;
    public  Item( String ID, int Amount, String Name, String Price,int order){
        this.ID=ID;
        this.Amount=Amount;
        this.Name=Name;
        this.Price=Price;
        this.order=order;
    }
    public String getID (){
        return this.ID;
    }
    public int getAmount (){
        return this.Amount;
    }
    public String getName (){
        return this.Name;
    }
    public String getPrice (){
        return this.Price;
    }
    public int getorder (){
        return this.order;
    }


}
