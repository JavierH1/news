@section('content')
    <!doctype html>
<html>
<!-- Image and text navbar-->
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Laravel News</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="http://127.0.0.1:8000/registernews">Registro de noticias</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<br/>

<head>

    <div class="container">
        <!-- Content here -->
        <br>
    </div>
    <meta charset="utf-8">
    <title>View of news</title>
    <meta name="description" content="views news">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css">
   <link rel="stylesheet"  href = "https://cdn.datatables.net/1.10.23/css/dataTables.bootstrap5.min.css">

</head>
<body>
<div class="container">

    <div class="col-sm-6">

        <p class="h1 text-center">Listado de noticias</p>

    </div>

    <table class =" table table-bordered">
        <tr>
            <th scope="row">id</th>
            <th scope="row">Titulo</th>
            <th scope="row">Autor</th>
            <th scope="row">Fuente</th>
            <th scope="row">URL</th>
            <th scope="row">URLImagen</th>
            <th scope="row">Descripción</th>
            <th scope="row">Contenido</th>
            <th scope="row">Fecha</th>
            <th scope="row">Operación</th>

        </tr>
        </thead>

        @foreach($listNews as $news)
            <tbody>

                <td>{{$news['id']}}</td>
                <td>{{$news['title']}}</td>
                <td>{{$news['author']}}</td>
                <td>{{$news['source']}}</td>
                <td>{{$news['url']}}</td>
                <td>{{$news['url_image']}}</td>
                <td>{{$news['description']}}</td>
                <td>{{$news['content']}}</td>
                <td>{{$news['published_at']}}</td>
                <td>



                    <a href = "{{"edit/".$news['id']}}" class="btn btn-primary btn-sm active" role=" button"
                       aria-pressed="=true">Editar</a>
                    <a href = "{{"delete/".$news['id']}}" class="btn btn-danger btn-sm active"
                       onclick="return confirm('¿Desea borrar esta noticia seleccionada?')"
                       role=" button"
                       aria-pressed="=true">Borrar</a>
                </td>
            </tr>
        </tbody>
        @endforeach
    </table>
    <div class="text-xs-center">
        <ul class="pagination justify-content-center">
            {{$listNews->onEachSide(5)->links()}}
        </ul>
    </div>

</div>

</body>

<br/>
<br/>
<br/>

<!-- footer -->
<footer class="bg-light text-left text-lg-start">
    <!-- Grid container -->
    <div class="container p-4">
        <!--Grid row-->

        <div>
            <h6 class="text-uppercase"> Project The News Api </h6>

            <!--Grid row-->
        </div>
        <!-- Grid container -->

        <!-- Copyright -->
        <div class="text-center p-4" style="background-color: #FAFAFA;">
            © 2021 Copyright:
            <a class="text-dark" href="https://mdbootstrap.com/">UCN - Desarrollo de Soluciones moviles</a>
        </div>
    </div>

    <!-- Copyright -->
</footer>
</html>
