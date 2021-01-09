@section('content')
    <!doctype html>
<html>
<!-- Image and text navbar-->
<nav class="navbar navbar-light" style="background-color: #e3f2fd;"><!-- prueba de color-->
    <div class="container-fluid">
    <span class="navbar-brand mb-0 h1">
            Universidad Católica del Norte
        </span>
    </div>
</nav>

<br/>

<head>
    <div class="container">
        <!-- Content here -->
        <br>
    </div>
    <meta charset="utf-8">
    <title>Editar Noticias</title>
    <meta name="description" content="register news">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>
<div class="container">

    <div class="col-sm-6">

        <p class="h1 text-center">Editar noticias</p>

    <!-- form start -->
        <form action="/edit" method="POST">

    @csrf
    <input type="hidden" name="id" value = "{{$editNews['id']}}"> <br> <br>
            <div class="form-group">
                <label for="Título title" class="col-sm-4 col-form-label">Título de la noticia</label>
                <div> <input type="text" class="form-control" name="title" value = "{{$editNews['title']}}"> </div>
            </div>

            <div class="form-group">
                <label for="author" class="col-sm-4 col-form-label">Autor</label>
                <div> <input type="text" class="form-control" name="author" value = "{{$editNews['author']}}"> </div>
            </div>

            <div class="form-group">
                <label for="source" class="col-sm-4 col-form-label">Fuente</label>
                <div> <input type="text" class="form-control" name="source" value = "{{$editNews['source']}}"> </div>
            </div>

            <div class="form-group">
                <label hidden for="url" class="col-sm-4 col-form-label">URL de la noticia</label>
                <div> <input type="hidden" class="form-control" name="url" value = "{{$editNews['url']}}"> </div>
            </div>

            <div class="form-group">
                <label for="url_image" class="col-sm-4 col-form-label">URL de la imagen</label>
                <div> <input type="text" class="form-control" name="url_image" value = "{{$editNews['url_image']}}"> </div>
            </div>

            <div class="form-group">
                <label for="description" class="col-sm-4 col-form-label">Descripcion de la noticia</label>
                <div> <input type="text" class="form-control" name="description" value = "{{$editNews['description']}}"> </div>
            </div>

            <div class="form-group ">
                <label for="content" class="col-sm-6 col-form-label">Contenido de la noticia</label>
                <div>
                    <textarea id ="content" class="form-control"name="content" rows="6" cols="50">{{$editNews->content}}
                    </textarea>
                </div>
            </div>
                <small id="inputContent" class="form-text text-muted">Ingrese contenido</small>
                <input type="hidden" name="date" value = "{{$editNews['published_at']}}">
            <br>
            <div>
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <div class="col-sm-10">
                    <button type="submit" class="btn btn-success">Registrar</button>
                </div>
            </div>
        </form>
    </div>
</div>
</body>
</div>

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
        <div class="text-center p-4" style="background-color: #e3f2fd;"">
        © 2021 Copyright:
        <a class="text-dark" href="https://mdbootstrap.com/">UCN -Desarrollo de Soluciones moviles</a>
    </div>
    <!-- Copyright -->
</footer>
</html>
