import { redirect } from '@sveltejs/kit';

/** @type {import('./$types').Actions} */
export const actions = {
    register: async({ request }) => {
        // TODO: Refactor to take in username of requester.

        const formData = await request.formData();

        const formJson = Object.fromEntries(formData.entries());

        // TODO: Migrate REST API address to the lib directory
        const response = await fetch('http://app:9090/api/v1/users', {
            method: 'POST',
            body: JSON.stringify(formJson),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        const responseJson = await response.json();

        console.log("RESPONSE: ");
        console.log(responseJson);

        // I don't like this
        throw redirect(301, '/dashboard/' + responseJson.id);

        // return { success: true };
    }
};



