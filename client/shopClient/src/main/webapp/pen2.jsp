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

	  	  
	  <div class="row">
	  	<img src="dist/img/pen2.jpg" alt="Pen2">
        <section class="col-sm-12">
          <table class="table table-bordered table-striped table-condensed">
            <caption>
            <h4>Pen n°2</h4>
            </caption>
         
            <tbody>
              <tr>
                <td>Name</td>
                <td>Pen2</td>
              </tr>
              <tr>
                <td>Price</td>
                <td>15$</td>
              </tr>
              <tr>
                <td>Details</td>
                <td>Black and silver pen</td>
              </tr>
              <tr>
                <td>References</td>
                <td>56258525</td>
              </tr>
            </tbody>
          </table>
        </section>
      </div>

          <p id="ok"></p>

          <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
          <script type="text/javascript" src="bower_components/jquery.soap/jquery.soap.js"></script>

          <script>
                  $.soap({
                      url: 'http://localhost:9763/services/Shop/',
                      namespaceURL:'http://shop.services.alma.org'
                  });
                  function ajouter()
                  {
                    $.soap({
                          method: 'addToCart',
                          data: {productReference: 56258525},
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

          </script>
	
	<a href="#" class="btn btn-lg btn-primary" onclick="ajouter()"><span class="glyphicon glyphicon-shopping-cart"></span> Ajouter</a>

    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="dist/js/bootstrap.min.js"></script>
  </body>
</html>
