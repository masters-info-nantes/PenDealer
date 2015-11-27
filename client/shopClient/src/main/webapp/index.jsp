<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<link rel="icon" href="dist/img/favicon2.ico">
<title>PenDealer</title>

<link href="dist/css/bootstrap.min.css" rel="stylesheet">
<link href="dist/css/first_template.css" rel="stylesheet">
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top">
<div class="container">
<div class="navbar-header">
<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
<span class="sr-only">Navigation</span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
<span class="icon-bar"></span>
</button>
    <a class="navbar-brand" href="index.html">Pen Dealer</a>
</div>
  <div id="navbar" class="collapse navbar-collapse">
    <ul class="nav navbar-nav">
			 <!--   <li class="active"><a href="http://localhost:9763/shopClient/index.jsp"></a></li> -->
		<li><a href="http://localhost:9763/shopClient/panier.jsp">Cart</a></li>
		<li><a href="http://localhost:9763/shopClient/about.jsp">About</a></li>
	</ul>
</div><!--/.nav-collapse -->
</div>
</nav>

<div class="container">

<div class="first_template">
<h1>Welcome !</h1>
</div>

<section class="row" id="pen"></section>

<script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
<script type="text/javascript" src="bower_components/jquery-xml2json/src/xml2json.js"></script>
<script type="text/javascript" src="bower_components/jquery.soap/jquery.soap.js"></script>

<script>

    console.log('1');
    $.soap({
        url: 'http://localhost:9763/services/Shop/',
        //namespaceURL:'http://impl.services.domain.shop.services.alma.org'
        namespaceURL:'http://shop.services.alma.org'
    });

       $.soap({
            method: 'GetProductsList',
            data: {},
            soap12: true,
            success: function (soapResponse) {
                // do stuff with soapResponse
                console.log('2');

                var tab = soapResponse.toJSON()["#document"]["ns:GetProductsListResponse"]["ns:return"];
                var liste = '<div class="inner">';
                var num;

                console.log(tab[0]);

                for(i=0;i<tab.length;i++)
                {
                  console.log('3:');
                  console.log(i);
                  num = i+1;
                  //liste += '<div class="col-xs-4 col-sm-3 col-md-2"><a href="http://localhost:9763/shopClient/penDetail.jsp" onclick="sessionStorage.setItem(\'currentpen\',\'' + tab[i]["reference"]["_"] + '\');"><img src="dist/img/' + tab[i]["reference"]["_"] + '.jpg" alt="Pen' + num + '" ></a></div>';

                    liste += '<div class="col-xs-4 col-sm-3 col-md-3">' +
                                 '<div class="well" >' +
                                      '<h3>' + tab[i]["name"]["_"] + '</h3>' +
                                            '<img style="max-width:225px" src="dist/img/' + tab[i]["reference"]["_"] + '.jpg"  alt="Pen' + num + '"/>' +
                                            '<p><b>Price:</b> <b>$' + tab[i]["price"]["_"] + '</b></p>' +
                                            '<p><b>Detail:</b> ' + tab[i]["details"]["_"] + '</p>' +
                                            '<p><b>Quantity: </b><input id="num'+ tab[i]["reference"]["_"] + '" type="number" value ="0" min = "1" /></br></p>'+
                                            '<p><input href="#" class="btn btn-block btn-primary btn-primary"' +
                                                    'onClick="addNum(\'' + tab[i]["reference"]["_"] + '\', $(\'#num' + tab[i]["reference"]["_"] + '\').val())" value="&#x2795 Add"></input></p>' +
                                                    //'onClick="ajouter(\'' + tab[i]["reference"]["_"] + '\')" value="&#x2795 Add"></input></p>' +
                                 '</div>' +
                              '</div>'
                }
                liste += '</div>'

                $("#pen").html(liste);
            },
            error: function (soapResponse) {
                console.log('that other server might be down...');
                console.log(soapResponse);
                console.log(soapResponse.toString());
            }
        });

    console.log('4');

    function addNum(ref,val){
        console.log('addNum');
        console.log('val: ');
        console.log(val);

            console.log("addNum for: ")
            console.log(i);
            ajouter(ref, val);

    }

     function ajouter(ref, val){
       $.soap({
             method: 'AddToCart',
             data: {productReference: ref},
             soap12: true,
             success: function (soapResponse) {
                 // do stuff with soapResponse
                 console.log(soapResponse);
                 console.log(soapResponse.toString());
                 console.log('add to cart');

                 if(val >1) {
                 ajouter(ref,val-1)
                 }
             },
             error: function (soapResponse) {
                 console.log('that other server might be down...');
                 console.log(soapResponse);
                 console.log(soapResponse.toString());

             }
         });

     }

</script>






<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="dist/js/bootstrap.min.js"></script>
</body>
</html>