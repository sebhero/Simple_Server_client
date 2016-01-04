//customOptions = {
//    view : {
//        alwaysVisible:true
//    },
//    labels: {
//        today: "Tod",
//        gotoDateInput: "Insert your date",
//        gotoDateButton: "Set",
//        clearButton: "Wipe"
//    }
//}
var elements = ['span', 'div', 'h1', 'h2', 'h3'];
var items;
var dataTest;
$.get("fr-svenska_2.txt",function(dataTest)
  {
    var items = dataTest;
//    $("#testTextarea").text("hello"+items);
//      elements=items;
//      arr=items;
      $("#testTextarea").append(dataTest);
    $("#testDiv").append(items);      
  });



$('#testTextarea').textcomplete([
    { // html
        match: /<(\w*)$/,
        search: function (term, callback) {
            callback($.map(elements, function (element) {
                return element.indexOf(term) === 0 ? element : null;
            }));
        },
        index: 1,
        replace: function (element) {
            return ['<' + element + '>', '</' + element + '>'];
        }
    }
]);