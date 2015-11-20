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
          <a class="navbar-brand" href="#">Pen Dealer</a>
        </div>
        <div id="navbar" class="collapse navbar-collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="http://localhost:9763/shopClient/index.jsp">Home</a></li>
            <li><a href="http://localhost:9763/shopClient/pen.jsp">Pen</a></li>
            <li><a href="http://localhost:9763/shopClient/panier.jsp">Panier</a></li>
            <li><a href="http://localhost:9763/shopClient/about.jsp">About</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container">

      <div class="first_template">
        <h1>Votre panier</h1>
      </div>
    
    
    <div class="row">
        <section class="col-sm-12">
          <table class="table table-bordered table-striped table-condensed">
            <caption>
            <h4>Panier</h4>
            </caption>
         
            <tbody id="panier">
              
            </tbody>
          </table>
        </section>
      </div>
    
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
                namespaceURL:'http://shop.services.alma.org'
            });

            $.soap({
                method: 'GetCart',
                data: { currency: curr },
                soap12: true,
                success: function (soapResponse) {

                    // do stuff with soapResponse
                    console.log(soapResponse.toJSON());
                                      
                    var tab = soapResponse.toJSON()["#document"]["ns:GetCartResponse"]["ns:return"];
                    var liste = '<tr><th>Name</th><th>Quantity</th><th>Price</th></tr>';
                    var totalPrice = 0;
                    var chiffreBizarre = "ax2446";

                    for(i = 0; i < tab.length; i++){

                        liste += '<tr>' + '<td>' + tab[i][chiffreBizarre+":product"]["name"]["_"] + '</td>'
                                + '<td>' + tab[i][chiffreBizarre+":quantity"] + '</td>'
                                + '<td>' + tab[i][chiffreBizarre+":totalPrice"] + ' ' + convertCurr(curr) + " (" + tab[i][chiffreBizarre+":product"]["price"]["_"] + ' unit)</td></tr>';

                        totalPrice += parseInt(tab[i][chiffreBizarre+":totalPrice"]);
                    }

                    liste += '<tr><td>TOTAL :</td><td></td><td>' + totalPrice + ' ' + convertCurr(curr) + '</td></tr>';

                    $("#panier").html(liste);                  
                },
                error: function (soapResponse) {
                    console.log('that other server might be down...');
                    console.log(soapResponse);
                    console.log(soapResponse.toString());

                    document.getElementById("ok").innerHTML="il ne marche pas !";
                }
            });
          }

          getCart("USD");

          function valide()
          {
              $.soap({
                url: 'http://localhost:9763/services/Shop/',
                namespaceURL:'http://shop.services.alma.org'
              }); 
                         
              $.soap({
                  method: 'ProcessOrder',
                  data: {},
                  soap12: true,
                  success: function (soapResponse) {
                      // do stuff with soapResponse
                      console.log(soapResponse);
                      console.log(soapResponse.toString());

                      document.getElementById("ok").innerHTML="valider !";
                  },
                  error: function (soapResponse) {
                      console.log('that other server might be down...');
                      console.log(soapResponse);
                      console.log(soapResponse.toString());

                      document.getElementById("ok").innerHTML="il ne marche pas !";
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
