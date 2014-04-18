function formhash(form, password) {
   //Crea una entrada de elemento nuevo, esta estará fuera del campo de contraseña con algoritmo hash.
   var p = document.createElement("input");
   //Agrega el elemento nuevo a nuestro formulario.
   form.appendChild(p);
   p.name = "p";
   p.type = "hidden"
   p.value = hex_sha512(password.value);
   //Asegúrate de que la contraseña en texto simple no sea enviada.
password.value = "";
   //Finalmente envía el formulario.
form.submit();
}