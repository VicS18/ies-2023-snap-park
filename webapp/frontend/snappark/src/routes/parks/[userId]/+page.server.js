/** @type {import('./$types').PageLoad} */
export async function load({ params }) {

    const uParks = await fetch('http://app:9090/api/v1/parks/' + params.userId);
    const user = params.userId;
    return {
        parks: uParks.json(),
        user: user,
    };
}