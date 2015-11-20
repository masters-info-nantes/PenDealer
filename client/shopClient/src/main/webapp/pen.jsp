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
        <h1>Your beautiful Pen</h1>
      </div>

      <section class="row" id="pen"></section>


        <script type="text/javascript" src="bower_components/jquery/dist/jquery.min.js"></script>
        <script type="text/javascript" src="bower_components/jquery-xml2json/src/xml2json.js"></script>
        <script type="text/javascript" src="bower_components/jquery.soap/jquery.soap.js"></script>

          <script>


          $.soap({
              url: 'http://localhost:9763/services/Shop/',
              namespaceURL:'http://shop.services.alma.org'
          });

          $.soap({
              method: 'GetProductsList',
              data: {},
              soap12: true,
              success: function (soapResponse) {
                  // do stuff with soapResponse
                  var tab = soapResponse.toJSON()["#document"]["ns:GetProductsListResponse"]["ns:return"];

                  var liste = '';
                  var num;

                  for(i=0;i<tab.length;i++)
                  {
/*
                        document.write(tab[i]["reference"]["_"]+"\n");
                        document.write(tab[i]["name"]["_"]+"\n");
                        document.write(tab[i]["details"]["_"]+"\n");
                        document.write(tab[i]["price"]["_"]+"\n\n");
*/
                        num = i+1;
                        liste += '<div class="col-xs-4 col-sm-3 col-md-2"><a href="http://localhost:9763/shopClient/penDetail.jsp" onclick="sessionStorage.setItem(\'currentpen\',\'' + tab[i]["reference"]["_"] + '\');"><img src="dist/img/' + tab[i]["reference"]["_"] + '.jpg" alt="Pen' + num + '" ></a></div>';

                  }

                  $("#pen").html(liste);
              },
              error: function (soapResponse) {
                  console.log('that other server might be down...');
                  console.log(soapResponse);
                  console.log(soapResponse.toString());

                  document.getElementById("ok").innerHTML="il ne marche pas !";
              }
          });



          </script>



<!--
	 <section class="row">
        <div class="col-xs-4 col-sm-3 col-md-2"><a href="http://localhost:9763/shopClient/pen1.jsp"><img src="dist/img/images.jpg" alt="Pen1" ></a></div>
        <div class="col-xs-4 col-sm-3 col-md-2"><a href="http://localhost:9763/shopClient/pen2.jsp"><img src="dist/img/pen2.jpg" alt="Pen2" ></a></div>
        <div class="col-xs-4 col-sm-3 col-md-2"><a href="http://localhost:9763/shopClient/pen3.jsp"><img src="dist/img/pen3.jpg" alt="Pen3" ></a></div>
        <div class="col-xs-4 col-sm-3 col-md-2"><a href="http://localhost:9763/shopClient/pen5.jsp"><img src="dist/img/pen5.jpg" alt="Pen5" ></a></div>
      </section>
-->
    </div><!-- /.container -->


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="dist/js/bootstrap.min.js"></script>
  </body>
</html>
