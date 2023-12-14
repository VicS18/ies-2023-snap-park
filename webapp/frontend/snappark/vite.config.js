import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
    plugins: [sveltekit()],
    vite: {
        ssr: {
            noExternal: ['chart.js']
        }
    }
});