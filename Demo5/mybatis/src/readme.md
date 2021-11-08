1.mybatis CRUD

2.一对多
<resultMap
    <collection
List<Product> products;
    
3.多对一
<resultMap
    <association
private Category category;


4.多对多
为了维系多对多关系，必须要一个中间表。 在这里我们使用订单项(OrderItem)表来作为中间表