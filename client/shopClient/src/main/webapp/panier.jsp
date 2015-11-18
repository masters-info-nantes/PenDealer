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
            <li class="active"><a href="#">Home</a></li>
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
         
            <tbody>
              <tr>
                <th>Name</th>
                <th>Quantity</th>
				<th>Price</th>
              </tr>
              <tr>
                <td></td>
				<td></td>
                <td></td>
              </tr>
              <tr>
                <td></td>
				<td></td>
                <td></td>
              </tr>
              <tr>
                <td></td>
				<td></td>
                <td></td>
              </tr>
			  <tr>
                <td></td>
				<td></td>
                <td></td>
              </tr>
			  <tr>
                <td>TOTAL :</td>
				<td></td>
                <td>$</td>
              </tr>
            </tbody>
          </table>
        </section>
      </div>

	<a href="#" class="btn btn-lg btn-primary"><span class="glyphicon glyphicon-arrow-right"></span> Valider</a>

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