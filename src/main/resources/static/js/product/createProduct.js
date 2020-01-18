function createProduct() {
  event.preventDefault();
  let name = document.getElementById('name').value;
  let file = document.getElementById('file').value;
  let barcode = document.getElementById('barcode').value;
  let expired_date = document.getElementById('expired_date').value;
  let price = document.getElementById('price').value;
  let count = document.getElementById('count').value;

  let xhr = new XMLHttpRequest();
  // xhr.setRequestHeader('Content-Type', 'multipart/form-data;');
  xhr.open("POST", "/iot/products");

  let request = {
    name: name,
    file: file,
    barcode: barcode,
    expired_date: expired_date,
    price: price,
    count: count
  };
  let body = JSON.stringify(request);
  xhr.send(body);
}
