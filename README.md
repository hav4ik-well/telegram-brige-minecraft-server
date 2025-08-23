# telegram-brige-minecraft

A bridge mod for automatic chat synchronization between Minecraft server and Telegram.

📦 Requirements
Minecraft: 1.21.6

Fabric Loader: 0.17.2+

Fabric API: 0.128.2+

Java: 17+

Telegram bot: Created via @BotFather

🚀 Installation
Server Installation
Download the latest .jar file from Releases

Place telegram-bridge-mod-1.0.0.jar in your server's mods/ folder

Ensure fabric-api.jar is also installed

Start the server to generate the configuration file

Configure config/telegram-bridge.json

Restart the server

Building from Source
```
git clone https://github.com/yourname/telegram-bridge-mod.git
cd telegram-bridge-mod
./gradlew build
```

⚙️ Configuration
After the first run, edit config/telegram-bridge.json:
```
{
  "botToken": "YOUR_TOKEN",
  "chatId": YOUR_ID,
  "formatFromMC": "[Minecraft] %s: %s",
  "formatFromTG": "§a[Telegram] §f%s: %s"
}
```
🤖 Telegram Setup
Create a bot via @BotFather

Send the /newbot command

Choose a name and username for the bot

Copy the bot token

Add the bot to your group/channel

Invite the bot as an administrator

Enable the "Send Messages" permission

Get the Chat ID

Send a message to the bot/in the group

Find the chat.id in the response

🎯 Usage
The mod works automatically:

Any message in Minecraft chat → sent to Telegram

Any message in Telegram chat → sent to Minecraft

No commands required - completely seamless operation

Example workflow:
```
Minecraft: "Hello world!" → Telegram: "[Minecraft] Player: Hello world!"
Telegram: "Hi from TG!" → Minecraft: "[Telegram] @user: Hi from TG!"
```
🛠️ Development
Cloning and Building
```
git clone https://github.com/yourname/telegram-bridge-mod.git
cd telegram-bridge-mod
./gradlew build

# The result will be in: build/libs/telegram-bridge-mod-1.0.0.jar
```

Dependencies
Fabric Loader API
Fabric Lifecycle Events API
Fabric Message API v1
Java Telegram Bot API 6.9.1
OkHttp 4.12.0
Kotlin STDlib 1.9.24

❓ Troubleshooting
Common Issues
Server crashes on startup

Ensure all dependencies are included in the build

Verify that Fabric API is installed on the server

Messages not synchronizing

Check the correctness of the bot token and chat ID

Ensure the bot has permission to send messages

ClassNotFound errors

Rebuild the mod with ./gradlew clean build

Logs
Check server logs for detailed information:

```
tail -f logs/latest.log | grep -i telegram
```
📝 License
This project is licensed under the MIT License - see the LICENSE file for details.

🤝 Contributing
Fork the project

Create a feature branch 
```
git checkout -b feature/amazing-feature
```

Commit your changes 
```
git commit -m 'Add amazing feature'
```

Push to the branch 
```
git push origin feature/amazing-feature
```

Open a Pull Request

📮 Support
Create an Issue for bugs or feature suggestions

Note: The mod requires a stable internet connection to communicate with the Telegram API. Messages may be delayed if there are connection issues.

⭐ If you like this mod, please give it a star on GitHub!
