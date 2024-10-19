<img width="1072" alt="Screenshot 2024-10-06 at 15 02 43" src="https://github.com/user-attachments/assets/b27a5a2a-32f9-4cd2-b5dd-ad088a0caaa5">

A simple GUI synthesizer that can generate sine waves, square waves, white noise, or connect a sine wave with a linear ramp to produce a variable frequency sine wave. The project also includes other components such as volume control, reverb, and a mixer.

Through this project, I learned how to convert a sine wave into discrete 16-bit (short) samples while considering little-endian format, and further convert these into 8-bit (byte) arrays. Using JavaFX, I applied object-oriented principles such as inheritance and interfaces by designing a base AudioComponentWidget class, with other components inheriting from it. I successfully synchronized the UI with the data, ensuring that whenever the user interacts with a component, the widget accurately updates its data and executes the corresponding methods.
