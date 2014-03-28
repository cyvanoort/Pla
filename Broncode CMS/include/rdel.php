<?php
include 'connect.php';
$total = $_POST['rec'];
$tot = explode('+',$total);
$id = $tot[0];
$file = "../reclame/".$tot[1];
$del = mysqli_query($link, 'DELETE FROM Reclames Where id="'.$id.'"') or die('er ging iets mis 11 ' . mysqli_error($link));
$kop = mysqli_query($link, 'DELETE FROM IR WHERE id_Reclames="'.$id.'"') or die('er ging iets mis 2222' . mysqli_error($link));
unlink($file);
echo 'verwijderd';
echo '<meta http-equiv="refresh" content="2; URL=http://plab.carstenoort-pt.com/cms/rwijzig1.php">';
?>