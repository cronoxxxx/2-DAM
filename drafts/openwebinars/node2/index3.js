const files = require('fs/promises');
const filesSycs = require('fs');

(async () => {
    try {
        if(!filesSycs.existsSync('./config')){
            await files.mkdir('./config')
        } else {
            await files.appendFile('./config/config.txt', 'lorem ipsum dolor sit amet')
            console.log('ya existe')
        }
    } catch (err) {
        console.error(err)
    }
})()
