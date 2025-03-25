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


## Testing different approaches
There are 2 approaches to handling the sucessfull and failure payments, the first approach inspects the url the user is redirected to. To test this one, go into the `CheckoutScren`
component and use the `CheckoutWebview`.

The second approach lets the javascript code call a function from android through javascript. To test this approach, switch the `CheckoutWebview` to `CheckoutWebviewCallbak`
