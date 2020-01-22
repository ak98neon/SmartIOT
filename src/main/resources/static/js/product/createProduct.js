function createProduct() {
  event.preventDefault();
  let name = document.getElementById('name').value;
  let fridge_id = document.getElementById('fridge_id').value;
  let file = document.getElementById('file').files[0];
  let barcode = document.getElementById('barcode').value;
  let expired_date = document.getElementById('expired_date').value;
  let price = document.getElementById('price').value;
  let count = document.getElementById('count').value;

  if (!validateFields(name, fridge_id, count, expired_date, price)) {
    return;
  }

  let xhr = new XMLHttpRequest();
  xhr.open("POST", "/iot/products");
  xhr.setRequestHeader("Content-Type", "application/json");

  let request = {
    name: name,
    barcode: barcode,
    expired_date: expired_date,
    price: price,
    count: count,
    file: file,
    fridge_id: fridge_id
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
      send(xhr, JSON.stringify(request));
    });
  } else {
    send(xhr, body);
  }
}

function send(xhr, body) {
  xhr.send(body);
}

function validateFields(name, fridgeId, count, expiredDate, price) {
  let isValid = true;
  if (name.length === 0) {
    document.getElementById('name').classList.add("is-invalid");
    isValid = false;
  }

  if (fridgeId.length === 0) {
    document.getElementById('fridge_id').classList.add("is-invalid");
    isValid = false;
  }

  if (count.length === 0) {
    document.getElementById('count').classList.add("is-invalid");
    isValid = false;
  }

  if (expiredDate.length === 0) {
    document.getElementById('expired_date').classList.add("is-invalid");
    isValid = false;
  }

  if (price.length === 0) {
    document.getElementById('price').classList.add("is-invalid");
    isValid = false;
  }
  return isValid;
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
