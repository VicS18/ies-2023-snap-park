export async function load({ params }) {
    const uSensors = await fetch('http://app:9090/api/v1/park/' + params.parkId + '/sensors');
    const park = await fetch('http://app:9090/api/v1/park/' + params.parkId);
    return {
        sensors: uSensors.json(),
        park: park.json(),
    };
}