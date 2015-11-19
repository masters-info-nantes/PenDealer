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
          <a class="navbar-brand" href="inde.html">Pen Dealer</a>
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
        <h1></h1>
      </div>
	  
	  <a href="http://localhost:9763/shopClient/pen.jsp" class="btn btn-default"><span class="glyphicon glyphicon-arrow-left"></span> Back</a>

	  	  
	  <div class="row" id="article">

      </div>


          <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
          <script type="text/javascript" src="bower_components/jquery-xml2json/src/xml2json.js"></script>
          <script type="text/javascript" src="bower_components/jquery.soap/jquery.soap.js"></script>

          <script>
                 $.soap({
                     url: 'http://localhost:9763/services/Shop/',
                     namespaceURL:'http://shop.services.alma.org'
                 });

                 function ajouter()
                 {
                    var ref = sessionStorage.getItem('currentpen');
                   $.soap({
                         method: 'AddToCart',
                         data: {productReference: ref},
                         soap12: true,
                         success: function (soapResponse) {
                             // do stuff with soapResponse
                             console.log(soapResponse);
                             console.log(soapResponse.toString());

                             document.getElementById("ok").innerHTML="c'est ajouter !";
                         },
                         error: function (soapResponse) {
                             console.log('that other server might be down...');
                             console.log(soapResponse);
                             console.log(soapResponse.toString());

                             document.getElementById("ok").innerHTML="ca ne marche pas !";
                         }
                     });

                    }

                  $.soap({
                      url: 'http://localhost:9763/services/Shop/',
                      namespaceURL:'http://shop.services.alma.org'
                  });

                  var ref = sessionStorage.getItem('currentpen');

                    $.soap({
                          method: 'GetProduct',
                          data: {productReference: ref},
                          soap12: true,
                          success: function (soapResponse) {

                              // do stuff with soapResponse
                              var pen = soapResponse.toJSON()["#document"]["ns:GetProductResponse"]["ns:return"];

                              var details = '<img src="dist/img/' + pen["ax2442:reference"] + '.jpg" alt="' + pen["ax2442:name"] + '">' +
                                      '<section class="col-sm-12">' +
                                        '<table class="table table-bordered table-striped table-condensed">' +
                                          '<tbody>' +
                                            '<tr>' +
                                              '<td>Name</td>' +
                                              '<td>' + pen["ax2442:name"] + '</td>' +
                                            '</tr>' +
                                            '<tr>' +
                                              '<td>Price</td>' +
                                              '<td>' + pen["ax2442:price"] + '$</td>' +
                                            '</tr>' +
                                            '<tr>' +
                                              '<td>Details</td>' +
                                              '<td>' + pen["ax2442:details"] + '</td>' +
                                            '</tr>' +
                                            '<tr>' +
                                              '<td>References</td>' +
                                              '<td>' + pen["ax2442:reference"] + '</td>' +
                                            '</tr>' +
                                          '</tbody>' +
                                        '</table>' +
                                      '</section>';

                              $("#article").html(details);
                          },
                          error: function (soapResponse) {
                              console.log('that other server might be down...');
                              console.log(soapResponse);
                              console.log(soapResponse.toString());

                          }
                      });

          </script>
	
	<a href="#" class="btn btn-lg btn-primary" onclick="ajouter()"><span class="glyphicon glyphicon-shopping-cart"></span> Ajouter</a>

    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="dist/js/bootstrap.min.js"></script>
  </body>
</html>
