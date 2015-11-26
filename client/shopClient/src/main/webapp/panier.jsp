<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <link rel="icon" href="dist/img/favicon.ico">

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
				<a class="navbar-brand" href="http://localhost:9763/shopClient/index.jsp">Pen Dealer</a>
		</div>
			  <div id="navbar" class="collapse navbar-collapse">
				<ul class="nav navbar-nav">
					<li><a href="http://localhost:9763/shopClient/panier.jsp">Cart</a></li>
					<li><a href="http://localhost:9763/shopClient/about.jsp">About</a></li>
				</ul>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container">
      <div class="first_template">
        <h1></h1>
      </div>
    
    
    <div class="row">
        <section class="col-sm-12">
          <table class="table table-bordered table-striped table-condensed">
            <caption>
            <h4>Cart :</h4>
            </caption>
         
            <tbody id="panier">
              
            </tbody>
          </table>
        </section>
      </div>

 <!--  Quantité : <input type="number" name="howmuch"> </td>		-->
 <!--  Bouton supprimer :  <a href="#" class="btn btn-block btn-primary btn-primary"><span class="glyphicon glyphicon-minus"></span> Supprimer</a> -->
 
	  
      <div class="row">
      <div class="col-md-6"></div>
        <select id="monnaie">
          <option value="USD">Dollar (&dollar;)</option>          
          <option value="EUR">Euro (&euro;)</option>
          <option value="INR">Rupee (&#x20b9;)</option>
          <option value="KRW">Won (&#8361; )</option>
        </select>
      <div class="col-md-5"></div>
	  
     </div>

          <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
          <script type="text/javascript" src="bower_components/jquery-xml2json/src/xml2json.js"></script>          
          <script type="text/javascript" src="bower_components/jquery.soap/jquery.soap.js"></script>

          <script>

          $("#monnaie").change(function(){
              getCart(this.value);
          });

          function convertCurr(val){
            var content = {
              'EUR': "&euro;",
              'USD': "&dollar;",
              'KRW': "&#8361;",
              'INR': "&#x20b9;"
            };

            return content[val];
          }


          function getCart(curr){
            $.soap({
                url: 'http://localhost:9763/services/Shop/',
                namespaceURL:'http://impl.services.domain.shop.services.alma.org'
            });

            $.soap({
                method: 'GetCart',
                data: { currency: curr },
                soap12: true,
                success: function (soapResponse) {

                    // do stuff with soapResponse
                    console.log('GetCart:');
                    console.log(soapResponse.toJSON());
                                      
                    var tab =$(soapResponse.toJSON()["#document"]["ns:GetCartResponse"]["ns:return"]).toArray();

                    var liste = '<tr><th>Name</th><th>Quantity</th><th>Price</th><th>RemoveItem</th></tr>';
                    var totalPrice = 0;

                    console.log('1');

                    for(i = 0; i < tab.length; i++){

                        var chiffreBizarre = (tab[i]["$"]["xsi:type"].split(":"))[0];

                        liste += '<tr>'
                                    + '<td>' + tab[i][chiffreBizarre+":product"]["name"]["_"] + '</td>'
                                    + '<td>' + '<input type="number" name="howmuch" value ="' + tab[i][chiffreBizarre+":quantity"] + '" min = "1" />' + '</td>'
                                    + '<td>' + tab[i][chiffreBizarre+":totalPrice"] + ' ' + convertCurr(curr) + " (" + tab[i][chiffreBizarre+":product"]["price"]["_"] + ' unit)</td>'
								    + '<td> <id="'+ tab[i][chiffreBizarre +":product"]["reference"]+'"><button class="btn btn-block btn-primary btn-primary" onclick="supprimer(' + [chiffreBizarre +":product"]["reference"] + ')" ><span class="glyphicon glyphicon-minus"></span> Supprimer</button></td>'
								+'</tr>';

                        totalPrice += parseInt(tab[i][chiffreBizarre+":totalPrice"]);
                    }

                    liste += '<tr><td>TOTAL :</td><td></td><td>' + totalPrice + ' ' + convertCurr(curr) + '</td><td></td></tr>';

                    $("#panier").html(liste);
                    console.log('2');
                },
                error: function (soapResponse) {
                    console.log('that other server might be down...');
                    console.log(soapResponse);
                    console.log(soapResponse.toString());
                }
            });
          }

          getCart("USD");
          console.log('3');


          function removeFromCart(ref){
              $.soap({
                  method: 'RemoveFromCart',
                  data: { productReference: ref },
                  soap12: true,
                  success: function (soapResponse) {
                      console.log('4');
                  },
                  error: function (soapResponse) {
                      console.log('removeFromCart might be down...');
                  }
              });
          }



          function supprimer(val){
                console.log("5");
                removeFromCart(val);
                getCart("USD");
                console.log('in supprimer');
          }

          function valide(){
              $.soap({
                url: 'http://localhost:9763/services/Shop/',
                namespaceURL:'http://impl.services.domain.shop.services.alma.org'
              }); 
                         
              $.soap({
                  method: 'ProcessOrder',
                  data: {},
                  soap12: true,
                  success: function (soapResponse) {
                      // do stuff with soapResponse
                      console.log(soapResponse);
                      console.log(soapResponse.toString());
                  },
                  error: function (soapResponse) {
                      console.log('that other server might be down...');
                      console.log(soapResponse);
                      console.log(soapResponse.toString());
                  }
              });
          }
          </script>

  <a href="#" class="btn btn-lg btn-primary" onclick="valide()"><span class="glyphicon glyphicon-arrow-right"></span> Valider</a>

    </div><!-- /.container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="dist/js/bootstrap.min.js"></script>



  </body>
</html>
