export interface Product{
    id?:number,
    name: string,
    description: string,
    price: number,
    quantity: number,
    manufacturer: string,
    totalPrice?:number,
    imageUrl: string,
    isChecked?: boolean
}