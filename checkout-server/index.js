const express = require('express');
const app = express();
const port = 3000;

app.use(express.static('public'));

app.get('/checkout', (req, res) => {
    res.send(`
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Checkout</title>
            <style>
                body { font-family: Arial, sans-serif; padding: 20px; }
                .container { max-width: 400px; margin: 0 auto; }
                .button { padding: 10px 20px; background-color: #007bff; color: white; border: none; cursor: pointer; }
                .button:hover { background-color: #0056b3; }
            </style>
        </head>
        <body>
            <div class="container">
                <h1>Checkout</h1>
                <p>Select your payment method:</p>
                <button class="button" onclick="payWithPix()">Pay with PIX</button>
                <button class="button" onclick="payWithCreditCard()">Pay with Credit Card</button>
            </div>
            <script>
                function payWithPix() {
                    window.Android.paymentSuccess();
                    // window.location.href = '/payment-success';
                }

                function payWithCreditCard() {
                    const success = Math.random() > 0.5;
                    if (success) {
                        window.Android.paymentSuccess();
                        // window.location.href = '/payment-success';
                    } else {
                        window.Android.paymentFailure();
                        // window.location.href = '/payment-failure';
                    }
                }
            </script>
        </body>
        </html>
    `);
});

// Payment success page
app.get('/payment-success', (req, res) => {
    res.send(`
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Payment Success</title>
            <style>
                body { font-family: Arial, sans-serif; padding: 20px; }
                .container { max-width: 400px; margin: 0 auto; text-align: center; }
                .success { color: green; font-size: 24px; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="success">✅ Payment Successful!</div>
                <p>Thank you for your purchase.</p>
            </div>
        </body>
        </html>
    `);
});

// Payment failure page
app.get('/payment-failure', (req, res) => {
    res.send(`
        <!DOCTYPE html>
        <html lang="en">
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Payment Failed</title>
            <style>
                body { font-family: Arial, sans-serif; padding: 20px; }
                .container { max-width: 400px; margin: 0 auto; text-align: center; }
                .failure { color: red; font-size: 24px; }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="failure">❌ Payment Failed</div>
                <p>Please try again.</p>
            </div>
        </body>
        </html>
    `);
});

app.listen(port, () => {
    console.log(`Fake checkout server running at http://localhost:${port}`);
});
