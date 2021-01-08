<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class News extends Model
{
    protected $fillable = [
        'title', 'author','source','url', 'url_image', 'description', 'content','published_at',
    ];
    protected $connection = 'sqlite';
    use HasFactory;
    protected $guarded = ["id"];

}
