# Webview POC

### How can i test it?
First you need to run the fake checkout server, if you have node installed on your machine, it's as simple as:

```bash
npm install
```

And then 

```bash
node index.js
```

Now, go into the mobile app and find the `CheckoutScreen` component and update the paymentUrl variable with your computer's ip address.

Now you can run your app and the checkout screen should appear.
