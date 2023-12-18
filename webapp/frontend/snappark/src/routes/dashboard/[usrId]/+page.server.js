console.log("Y");

/** @type {import('./$types').Actions} */
export const actions = {
    create: async({ request, params }) => {
        // TODO: Refactor to take in username of requester.

        const usrId = params.usrId;

        const formData = await request.formData();

        const formJson = Object.fromEntries(formData.entries());

        // TODO: Migrate REST API address to the lib directory
        const response = await fetch('http://app:9090/api/v1/parks/manager/' + usrId, {
            method: 'POST',
            body: JSON.stringify(formJson),
            headers: {
                'Content-Type': 'application/json'
            }
        });

        return { success: true };
    }
};

/** @type {import('./$types').PageLoad} */
export async function load({params}) {
    // TODO: Refactor to load specified client's parks

    // Fetch manager user's parks

    const usrId = params.usrId;

    const response = await fetch('http://app:9090/api/v1/parks/manager/' + usrId);
    return {
        parks: response.json(),
        usrId: usrId
    };
}