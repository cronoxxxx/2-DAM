const readline = require('readline')
const fs = require('fs')

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
})

rl.question('What is your name? ', (name) => {
    rl.question('Where do you live? ', (location) => {
        console.log(`Hi ${name} from ${location}`)
        const stream = fs.createWriteStream(`./ficheros/${name}.md`)
        rl.setPrompt(`What's on your mind, ${name}? `)
        rl.prompt()
        rl.on('line', (line) => {
            if (line.trim().toLowerCase() === 'exit') {
                stream.close()
                rl.close()
            } else {
                stream.write(`${name}: ${line}\n`)
                rl.prompt()
            }
        })
    })
})

rl.on('close', () => {
    console.log('Have a great day!')
})
