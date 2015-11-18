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
            <li class="active"><a href="http://localhost:9763/index.html">Home</a></li>
			      <li><a href="http://localhost:9763/pen.html">Pen</a></li>
            <li><a href="http://localhost:9763/panier.html">Panier</a></li>
			      <li><a href="http://localhost:9763/about.html">About</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>

    <div class="container">

      <div class="first_template">
        <h1></h1>
      </div>
	  
	  <a href="http://localhost:9763/pen.html" class="btn btn-default"><span class="glyphicon glyphicon-arrow-left"></span> Back</a>
	  
	  
	  <div class="row">
	  	<img src="dist/img/images.jpg" alt="Pen1">
        <section class="col-sm-12">
          <table class="table table-bordered table-striped table-condensed">
            <caption>
            <h4>Pen n°1</h4>
            </caption>
         
            <tbody>
              <tr>
                <td>Name</td>
                <td>Pen1</td>
              </tr>
              <tr>
                <td>Price</td>
                <td>10$</td>
              </tr>
              <tr>
                <td>Details</td>
                <td>Box of ten wax pencil</td>
              </tr>
              <tr>
                <td>References</td>
                <td>27854525</td>
              </tr>
            </tbody>
          </table>
        </section>
      </div>    
	
	<a href="#" class="btn btn-lg btn-primary"><span class="glyphicon glyphicon-shopping-cart"></span> Ajouter</a>
	
    </div><!-- /.container -->


    <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
            <script type="text/javascript" src="bower_components/jquery.soap/jquery.soap.js"></script>

            <p id="credit"></p>

            <script>
            $.soap({
                url: 'http://localhost:9763/services/Bank/',
                namespaceURL:'http://bank.services.alma.org'
            });
            $.soap({
                method: 'GetCredit',
                data: {},
                soap12: true,
                success: function (soapResponse) {
                    // do stuff with soapResponse
                    console.log(soapResponse);
                    console.log(soapResponse.toString());

                    document.getElementById("credit").innerHTML=soapResponse;
                },
                error: function (soapResponse) {
                    console.log('that other server might be down...');
                    console.log(soapResponse);
                    console.log(soapResponse.toString());
                }
            });
            </script>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script src="dist/js/bootstrap.min.js"></script>
  </body>
</html>
