function createProduct() {
  event.preventDefault();
  let name = document.getElementById('name').value;
  let file = document.getElementById('file').files[0];
  let barcode = document.getElementById('barcode').value;
  let expired_date = document.getElementById('expired_date').value;
  let price = document.getElementById('price').value;
  let count = document.getElementById('count').value;

  let xhr = new XMLHttpRequest();
  xhr.open("POST", "/iot/products");
  xhr.setRequestHeader("Content-Type", "application/json");

  let request = {
    name: name,
    barcode: barcode,
    expired_date: expired_date,
    price: price,
    count: count,
    file: file
  };
  let body = JSON.stringify(request);

  xhr.onreadystatechange = function () {
    if (xhr.readyState === 4 && xhr.status === 200) {
      console.log(xhr.responseText);
    }
  };

  if (document.getElementById('file').files.length > 0) {
    getBase64(file, function (result) {
      request.file = result.replace(/^data:image\/(png|jpg);base64,/, "");
      let body = JSON.stringify(request);
      sendData(xhr, body);
    });
  } else {
    sendData(xhr, body);
  }
}

function sendData(xhr, body) {
  xhr.send(body);
}

function getBase64(file, cb) {
  let reader = new FileReader();
  reader.readAsDataURL(file);
  reader.onload = function () {
    cb(reader.result);
  };
  reader.onerror = function (error) {
    console.log('Error: ', error);
  };
}
