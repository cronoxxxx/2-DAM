const fs = require('fs');
const fsPromise = require('fs/promises');

//fs.readFile('./package.json', 'utf8', (err, data) => {
//    if (err) {
//        console.error(err);
//        return;
//    }

//    console.log(data);
//})




//Async-await:comun
(async () => {
    const filesPromise = await fsPromise.readdir('./ficheros')
    console.log(`ASYNC AWAIT`, filesPromise);
})()


//Difieren del async-await COMUN
//Sincrona
const files = fs.readdirSync('./ficheros')
console.log(`SYNC`,files)


//Asincrona con callback
fs.readdir('./ficheros', (err, files) => {
    if (err) {
        console.error(err);
        return;
    }
    console.log(`ASYNC`, files);
})





//Asincrona con promise:comun
fsPromise.readdir('./ficheros')
    .then(files => console.log(`ASYNC PROMISE`, files))
    .catch(err => console.error(err))



