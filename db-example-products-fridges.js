// Step1
// let's add some food to our Products DB!
db.Products.insert({"name": "Coke", "description" : "a bottle of coke", "lasts" : 30, "imageUrl" : "http://someurl.com/picture.jpg"})
db.Products.insert({"name": "Pepsi", "description" : "a can of pepsi", "lasts" : 30, "imageUrl" : "http://someurl.com/picture.jpg"})
db.Products.insert({"name": "Lasagna", "description" : "some vegan lasagna", "lasts" : 30, "imageUrl" : "http://someurl.com/picture.jpg"})
db.Products.find();

// all the documents aren't equal!
db.Products.insert({"name": "Eggplant", "description" : "eggplants from the market", "lasts" : 5, "imageUrl" : "http://someurl.com/picture.jpg", "organic" : true})
db.Products.insert({"name": "Eggs", "description" : "eggs from the market", "lasts" : 20, "imageUrl" : "http://someurl.com/picture.jpg", "organic" : true})
db.Products.find();

// we query by example...
db.Products.find({"name" : "Eggplant", "lasts" : 5});

// powerful update options but beware!
db.Products.update({"name" : "Coke"}, {"name" : "Coca Cola"});
db.Products.find({"name" : "Coca Cola"});
db.Products.update({"name" : "Coke"}, {$set : {"name" : "Coca Cola"}}, {"multi" : true});

db.Products.update({"lasts" : 30}, {$inc : {"lasts" : 10}});
db.Products.find();

// let's create the indexes
// current status with explain()
db.Products.find({"name" : "Eggplant"}).explain();
db.Products.ensureIndex({name:1},{unique:true, dropDups: true});
db.Products.find({"name" : "Eggplant"}).explain();			

// we can run JS scripts directly
db.Products.find({"organic" :{$exists:1}}).forEach(function(product) {
            db.Products.update(product, {$inc : {"lasts" : 5}});
            print(product);});
   
// Step2         
// lets create the Fridges
db.Fridges.insert({"name" : "Home", "description" : "my home fridge", "putOrRemoveLog" : []});
db.Fridges.insert({"name" : "Work", "description" : "my work fridge"});
db.Fridges.ensureIndex({name:1},{unique:true, dropDups: true});
db.Fridges.dropIndexes();
db.Fridges.find();

// let's add a log entry
db.Products.find();
db.Fridges.update({"name" : "Home"}, {"$push" : {"putOrRemoveLog" : {"product" : "Coca Cola", 
    "timestamp" : 1382520197,
    "isPut" : true}}});
db.Fridges.find();
    
// let's play with the aggregation framework
db.Products.aggregate(
  [
    { $project : { name:{$toUpper:"$name"} , _id:0 } },
    { $sort : { name : 1 } }
  ]
)
